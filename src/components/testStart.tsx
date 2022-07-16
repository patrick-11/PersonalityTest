import React from "react"


import ButtonCenter from "./buttonCenter"

import { TestStartProps } from "../types/interfacesTest"


const TestStart = (props: TestStartProps) => {

	const onInTestTrue = () => {props.onInTestChange(true)}

	const onRegisterTrue = () => (props.onRegisterShow(true))

	if(props.user.name.length <= 0) {
		return (
			<ButtonCenter
				name = {"Register"}
				size = {"lg"}
				variant = {"primary"}
				action = {onRegisterTrue}
			/>
		)
	}
	else {
		return (
			<ButtonCenter
				name = {"Start Test"}
				size = {"lg"}
				variant = {"success"}
				action = {onInTestTrue}
			/>
		)
	}
}

export default TestStart