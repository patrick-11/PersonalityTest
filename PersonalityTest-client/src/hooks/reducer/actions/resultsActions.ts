import { ActionTypes } from "../constants/actionTypes"
import { Result } from "../resultsReducer"


export const resultsActions = {

	getResults: () => {return {type: ActionTypes.GET_RESULTS, payload: null}},

	getResult: (id: string) => {return {type: ActionTypes.GET_RESULT, payload: id}},

	setResults: (results: Array<Result>) => {return {type: ActionTypes.SET_RESULTS, payload: results}},

	setResult: (result: Result) => {return {type: ActionTypes.SET_RESULT, payload: result}},

	updateResult: (result: Result) => {return {type: ActionTypes.UPDATE_RESULT, payload: result}},

	deleteResult: (id: string) => {return {type: ActionTypes.DELETE_RESULT, payload: id}},

}