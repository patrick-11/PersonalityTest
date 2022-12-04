import axios from "axios"
import type { User } from "../hooks/reducer/UsersReducer"
import type { Result } from "../hooks/reducer/ResultsReducer"
import { errorCheck } from "../util/Util"

const url = "http://localhost:8080/api/users/"


export const UsersAPI = {

  getUsers: () => {
    return new Promise<Array<User>>((resolve, reject) => {
      axios({method: "GET", url: url})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  },

  getUser: (id: string) => {
    return new Promise<User>((resolve, reject) => {
      axios({method: "GET", url: url + id})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  },

  getUserResults: (id: string) => {
    return new Promise<Array<Result>>((resolve, reject) => {
      axios({method: "GET", url: url + id + "/results"})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  },


  createUser: (user: User) => {
    return new Promise<User>((resolve, reject) => {
      axios({method: "POST", url: url, data: JSON.stringify(user), headers: {"Content-Type": "application/json"}})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  },

  createUserById: (id: string, user: User) => {
    return new Promise<User>((resolve, reject) => {
      axios({method: "POST", url: url + id, data: JSON.stringify(user), headers: {"Content-Type": "application/json"}})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  },

  updateUser: (user: User) => {
    return new Promise<User>((resolve, reject) => {
      axios({method: "PUT", url: url + user.id, data: JSON.stringify(user), headers: {"Content-Type": "application/json"}})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  },

  deleteUser: (id: string) => {
    return new Promise<boolean>((resolve, reject) => {
      axios({method: "DELETE", url: url + id})
        .then(response => resolve(response.data))
        .catch(error => reject(errorCheck(error)))
    })
  }
}