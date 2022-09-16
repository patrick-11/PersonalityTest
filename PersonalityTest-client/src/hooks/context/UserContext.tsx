import React, { createContext, useState } from "react"


const userDefault = {
  id: "",
  name:"",
  gender: "",
  age: 50,
  registered: false,
  inTest: false,
  questionIndex: 0,
  answers: []
}

export interface UserInterface {
  id: string,
  name: string,
  gender: string,
  age: number,
  registered: boolean,
  inTest: boolean,
  questionIndex: number,
  answers: Array<number>
}

interface UserContextInterface {
  user: UserInterface,
  updateUser: (user: UserInterface) => void,
  resetUser: () => void,
  incrementQuestionIndex: () => void,
  decrementQuestionIndex: () => void
}

interface UserContextProviderProps {
  children: React.ReactNode
}


export const UserContext = createContext<UserContextInterface>({} as UserContextInterface)

export const UserContextProvider = ({ children }: UserContextProviderProps) => {
  const [user, setUser] = useState<UserInterface>(() => {return JSON.parse(localStorage.getItem("user") || JSON.stringify(userDefault))})

  const updateUser = (user: UserInterface) => {
    setUser(user)
    localStorage.setItem("user", JSON.stringify(user))
  }

  const resetUser = () => {updateUser(userDefault)}

  const incrementQuestionIndex = () => {
    if(user.questionIndex < 10) {updateUser({...user, questionIndex: user.questionIndex + 1})}
  }

  const decrementQuestionIndex = () => {
    if(user.questionIndex > 0) {updateUser({...user, questionIndex: user.questionIndex - 1})}
  }

  return (
    <UserContext.Provider value={{ user, updateUser, resetUser, incrementQuestionIndex, decrementQuestionIndex }}>
      {children}
    </UserContext.Provider>
  )
}