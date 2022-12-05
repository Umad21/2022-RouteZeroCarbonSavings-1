import { readText } from "./file_reader.js";

/*

outline of response
[
    {
        currentCO2e,
        newCO2e (just a weighted average; sum{emissions * probability}),
        alternatives[
            {
                transport
                probability
                CO2e
            },{
                transport
                probability
                CO2e 
            }, ...
        ]
    },{
        currentCO2e
        newCO2e
        alternatives[
            ...
        ]
    },
    ...
]

ideas for generating the required graphs

[before]
transport counts -> tally up distinct travel types over the response array 
CO2e -> total up CO2e contributions of each transport type, this can be done by mapping a surface-level parse of the csv and indexing CO2e and adding to correspoding travel type

[after]
transport counts -> do weighted sum of probabilities across every travel record OR take the largest probability for each travel record and tally this way
CO2e -> tally up probability weighted CO2e across every travel record, allocate it to the corresponding travel type 

*/

const parseCSVToList = (csvString) => { //returns list of strings
    const lines = csvString.split(/\r?\n/);
    return lines.map((x) => { 
        const rowFields = x.split(",")
        return rowFields[rowFields.length - 1];
    });
}

export const getTravelListOfCSV = (csvFileObj, travelList) => {
    readText(csvFileObj, async (event) => {
        const text = event.target.result;
        travelList = parseCSVToList(text);
    });
}
