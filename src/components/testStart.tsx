import React from "react";


import { Button } from "react-bootstrap";


const TestStart = (props: any) => {
	return (
		<div className="text-center">
			<Button
				size="lg"
				onClick = {
					() => {
						if(props.profile.username.length === 0) {props.onRegisterShow(true)}
						else {props.onInTestChange(true)}
					}
				}
			>
				{props.profile.username.length ? "Start Test" : "Register"}
			</Button>
		</div>
	)
}

export default TestStart;