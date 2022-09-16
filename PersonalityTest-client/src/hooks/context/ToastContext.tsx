import React, { useState, createContext } from "react"


const toastDefault = {
  show: false,
  title: "",
  message: "",
  color: ""
}

interface ToastInterface {
  show: boolean,
  title: string,
  message: string,
  color: string
}

interface ToastContextInterface {
  toasts: Array<ToastInterface>,
  onToastAdd: (toast: ToastInterface) => void,
  onToastShow: (index: number, state: boolean) => void
}

interface ToastContextProviderProps {
  children: React.ReactNode
}


export const ToastContext = createContext<ToastContextInterface>({} as ToastContextInterface)

export const ToastContextProvider = ({ children }: ToastContextProviderProps) => {
  const [toasts, setToasts] = useState<Array<ToastInterface>>([toastDefault])

  const onToastAdd = (toast: ToastInterface) => {setToasts((prevToasts) => ([...prevToasts, toast]))}

  const onToastShow = (index: number, state: boolean) => {
    toasts.forEach((toast, idx) => {if(index === idx) {
      toast.show = state
      setToasts((prevToasts) => ([...prevToasts, toast]))
    }})
  }

  return (
    <ToastContext.Provider value={{ toasts, onToastAdd, onToastShow }}>
      {children}
    </ToastContext.Provider>
  )
}
