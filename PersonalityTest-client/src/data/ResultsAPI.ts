import axios from "axios"
import type { Result } from "../hooks/reducer/ResultsReducer"
import { errorCheck } from "../util/Util"

const url = "http://localhost:8080/api/v1/results/"


export const ResultsAPI = {

  getResults: () => {
    return new Promise<Array<Result>>((resolve, reject) => {
      axios({method: "GET", url: url})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  },

  getResult: (id: string) => {
    return new Promise<Result>((resolve, reject) => {
      axios({method: "GET", url: url + id})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  },

  createResult: (userId: string, answers: Array<number>) => {
    return new Promise<Result>((resolve, reject) => {
      axios({method: "POST", url: url + userId, data: JSON.stringify({"answers": answers}), headers: {"Content-Type": "application/json"}})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  },

  updateResult: (id: string, answers: Array<number>) => {
    return new Promise<Result>((resolve, reject) => {
      axios({method: "PUT", url: url + id, data: JSON.stringify({"answers": answers}), headers: {"Content-Type": "application/json"}})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  },

  deleteResult: (id: string) => {
    return new Promise<boolean>((resolve, reject) => {
      axios({method: "DELETE", url: url + id})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  }
}