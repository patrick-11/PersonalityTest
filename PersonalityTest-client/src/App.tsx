import React from "react"

import Router from "./screens/Router"
import RegisterModal from "./components/RegisterModal"
import Toaster from "./components/Toaster"
import { RegisterContextProvider } from "./hooks/context/registerContext"
import { ToastContextProvider } from "./hooks/context/toastContext"
import { UserContextProvider } from "./hooks/context/userContext"


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