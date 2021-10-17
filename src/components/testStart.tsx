import React from "react"


import { Button } from "react-bootstrap"

import { TestStartProps } from "../types/interfacesTest"


const TestStart = (props: TestStartProps) => {
	return (
		<div className="text-center">
			<Button
				size="lg"
				onClick = {
					() => {
						if(props.user.name.length === 0) {props.onRegisterShow(true)}
						else {props.onInTestChange(true)}
					}
				}
			>
				{props.user.name.length ? "Start Test" : "Register"}
			</Button>
		</div>
	)
}

export default TestStart