import { ActionTypes } from "../constants/ActionTypes"
import { User } from "../UsersReducer"


export const UsersActions = {

  getUsers: () => {return {type: ActionTypes.GET_USERS, payload: null}},

  setUsers: (users: Array<User>) => {return {type: ActionTypes.SET_USERS, payload: users}},

  setUser: (user: User) => {return {type: ActionTypes.SET_USER, payload: user}},

  updateUser: (user: User) => {return {type: ActionTypes.UPDATE_USER, payload: user}},

  deleteUser: (id: string) => {return {type: ActionTypes.DELETE_USER, payload: id}},

}
