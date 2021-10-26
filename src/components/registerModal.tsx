import React, { useState } from "react"


import { Button, Form, Modal, InputGroup, FormControl, FormGroup, FormLabel, Alert, ButtonGroup, ToggleButton} from "react-bootstrap"

import { RegisterModalProps } from "../types/interfacesApp"


const RegisterModal = (props: RegisterModalProps) => {
	const genderOptions = ["Male", "Female"]
	const [profile, setProfile] = useState({name: "", gender: "", age: 50})


	const onCreateProfileChange = (event: any) => {
		event.persist()
		setProfile((prevProfile) => ({...prevProfile, [event.target.name]: event.target.value}))
	}

	const onCreateNameDelete = () => {setProfile((prevProfile) => ({...prevProfile, name: ""}))}

	const onCreateProfileReset = () => {setProfile({name: "", gender: "", age: 50})}

	return (
		<Modal show = {props.registerShow} onHide = {() => props.onRegisterShow(false)}>
			<Modal.Header closeButton>
				<Modal.Title>Register</Modal.Title>
			</Modal.Header>
			
			<Modal.Body>
				{
					props.registerFail ?
						<Alert variant = "danger">{props.registerFail}</Alert>
						:
						<></>
				}
				<Form>
					<FormGroup>
						<FormLabel>Name:</FormLabel>
						<InputGroup>
							<InputGroup.Text>@</InputGroup.Text>
							<FormControl
								placeholder = "Name"
								type = "text"
								name = "name"
								value = {profile.name}
								onChange = {onCreateProfileChange}
							/>

							<Button
								variant = "outline-secondary"
								disabled = {!profile.name.length}
								onClick = {onCreateNameDelete}
							>
								X
							</Button>
						</InputGroup>
						{profile.name.length > 10 ?
							<Form.Text>Maximum 10 Characters!</Form.Text>
							:
							<></>
						}
					</FormGroup>
					<hr/>
					<FormGroup>
						<FormLabel>Gender:</FormLabel>
						<InputGroup>
							<ButtonGroup>
								{genderOptions.map((option, index) => (
									<ToggleButton
										key = {index}
										id={`radio-${index}`}
										type = "radio"
										variant = "outline-primary"
										name = "gender"
										value = {option}
										checked = {profile.gender === option}
										onChange = {onCreateProfileChange}
									>
										{option}
									</ToggleButton>
								))}
							</ButtonGroup>
						</InputGroup>
					</FormGroup>
					<hr/>
					<FormGroup>
						<FormLabel>Age: {profile.age}</FormLabel>
						<Form.Range
							id = "age"
							name = "age"
							min = "5"
							max = "120"
							onChange = {onCreateProfileChange}
						/>
					</FormGroup>


				</Form>
			</Modal.Body>

			<Modal.Footer>
					<Button
						variant = "secondary"
						onClick = {() => props.onRegisterShow(false)}
					>
						Close
					</Button>
					<Button
						disabled = {
							!profile.name.length ||
							profile.name.length > 10 ||
							!profile.gender.length
						}
						onClick = {() => {
							props.onRegister(profile)
							onCreateProfileReset()
						}}
					>
						Register
					</Button>
			</Modal.Footer>
		</Modal>
	)
}

export default RegisterModal