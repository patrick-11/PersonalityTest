import { ActionTypes } from './constants/ActionTypes'


export interface User {
  id?: string,
  name: string,
  gender: string,
  age: number
}

export const UsersReducer = (users: Array<User>, { type, payload }: any) => {
  switch (type) {
    case ActionTypes.GET_USERS:
      return users
    case ActionTypes.SET_USERS:
      return payload
    case ActionTypes.SET_USER:
      return [...users, payload]
    case ActionTypes.UPDATE_USER:
      return [...users.filter((user: User) => {return user.id !== payload.id}), payload]
    case ActionTypes.DELETE_USER:
      return users.filter((user: User) => {return user.id !== payload})
  }
}
