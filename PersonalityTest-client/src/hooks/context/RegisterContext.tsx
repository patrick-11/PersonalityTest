import React, { useState, createContext } from "react"


const registerDefault = {
  show: false,
  message: ""
}

interface RegisterInterface {
  show: boolean,
  message: string
}

interface RegisterContextInterface {
  register: RegisterInterface,
  onRegisterShow: (state: boolean) => void,
  onRegisterFail: (message: string) => void
}

type RegisterContextProviderProps = {
  children: React.ReactNode
}


export const RegisterContext = createContext<RegisterContextInterface>({} as RegisterContextInterface)

export const RegisterContextProvider = ({ children }: RegisterContextProviderProps) => {
  const [register, setRegister] = useState<RegisterInterface>(registerDefault)

  const onRegisterShow =(state: boolean) => {setRegister((prevRegister) => ({...prevRegister, "show": state}))}

  const onRegisterFail =(message: string) => {setRegister((prevRegister) => ({...prevRegister, "message": message}))}

  return (
    <RegisterContext.Provider value={{ register, onRegisterShow, onRegisterFail }}>
      {children}
    </RegisterContext.Provider>
  )
}
