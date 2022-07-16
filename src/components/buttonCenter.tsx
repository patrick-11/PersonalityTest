import React from "react"


import { Button } from "react-bootstrap"

import { ButtonCenterProps } from "../types/interfacesApp"


const buttonCenter = (props: ButtonCenterProps) => {
	return (
		<div className = "text-center">
			<Button
				size = {props.size}
				variant = {props.variant}
				onClick = {() => {props.action()}}
			>
				{props.name}
			</Button>
	</div>
	)
}

export default buttonCenter