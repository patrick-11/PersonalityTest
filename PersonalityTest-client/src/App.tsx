import React from "react"

import Router from "./screens/Router"
import RegisterModal from "./components/RegisterModal"
import Toaster from "./components/Toaster"
import { RegisterContextProvider } from "./hooks/context/RegisterContext"
import { ToastContextProvider } from "./hooks/context/ToastContext"
import { UserContextProvider } from "./hooks/context/UserContext"


const App = () => {
  return (
    <RegisterContextProvider>
      <ToastContextProvider>
        <UserContextProvider>

            <Router/>
            <RegisterModal/>
            <Toaster/>

        </UserContextProvider>
      </ToastContextProvider>
    </RegisterContextProvider>
  )
}

export default App