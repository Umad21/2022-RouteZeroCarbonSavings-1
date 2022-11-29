import React, {Component, useEffect, ReactDOM} from "react";
import {Container} from "react-bootstrap";

import {readText} from '../../helpers/file_reader.js';
import {Chart} from '../../components/Chart/Chart.js';

//import axios from '../../../node_modules/axios';

import "./View.scss";

const routeZeroApi = "https://emissions-qzzieui6kq-ew.a.run.app"

const api_key = "replace-me-with-api-key"

axios.get(routeZeroApi, {}, {
    auth: {
      api_key
    }
  })
  .then(function(response) {
    console.log(response.data, 'api response');
  })

export const View = (props) => {

    useEffect(() => {
        document.title = "Graphs | RouteZero"
    }, []);

    return(<>
            <h1>Visualisation</h1>
            <p>{JSON.stringify(props.response)}</p>

            getCustomerData()

            <div className="center-grid">
                <div className="cell">
                    <h2>Before</h2>
                    <div className="Chart">
                        <Chart/>
                    </div>
                    <div className="Chart">
                        <Chart/>
                    </div>
                </div>
                <div className="cell">
                    <h2>After</h2>
                    <div className="Chart">
                        <Chart/>
                    </div>
                    <div className="Chart">
                        <Chart/>
                    </div>
                </div>
            </div>

        </>
    )
}