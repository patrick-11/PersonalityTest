import { ActionTypes } from "./constants/actionTypes"
import { User } from "./usersReducer"


export interface Result {
	id: string,
	completed: string,
	user: User,
	answers: Array<number>
	results: Array<number>
	avgScore: number
}

export const resultsReducer = (results: Array<Result>, { type, payload }: any) => {
  switch (type) {
	case ActionTypes.GET_RESULTS:
		return results
	case ActionTypes.GET_RESULT:
		return results.filter((result: Result) => {return result.id === payload})
	case ActionTypes.GET_RESULTS_BY_USERID:
		return results.filter((result: Result) => {return result.user.id === payload})
	case ActionTypes.SET_RESULTS:
		return payload
	case ActionTypes.SET_RESULT:
		return [...results, payload]
	case ActionTypes.UPDATE_USER:
		return [...results.filter((result: Result) => {return result.id !== payload.id}), payload]
	case ActionTypes.DELETE_RESULT:
		return results.filter((result: Result) => {return result.id !== payload})
	default:
		return results
	}
}
