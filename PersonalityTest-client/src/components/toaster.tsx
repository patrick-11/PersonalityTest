import React, { useContext } from "react"

import { Toast, ToastContainer } from "react-bootstrap"
import { ToastContext } from "../hooks/context/toastContext"


const Toaster = () => {
	const toastContext  = useContext(ToastContext)

	return (
		<ToastContainer className="p-4" position="bottom-end">
				{toastContext.toasts.map((toast, index) => {
						return (
							<Toast key={index} show={toast.show} bg={toast.color} onClose={() => toastContext.onToastShow(index, false)} delay={4000} autohide>
								<Toast.Header>
										<strong className="me-auto">{toast.title}</strong>
								</Toast.Header>
								<Toast.Body>{toast.message}</Toast.Body>
							</Toast>
						)
				})}
		</ToastContainer>
	)
}

export default Toaster