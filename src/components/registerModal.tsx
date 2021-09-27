import React, { useState } from "react";


import { Button, Form, Modal, InputGroup, FormControl, FormGroup, FormLabel, Alert, ButtonGroup, ToggleButton} from "react-bootstrap";

import { RegisterModalProps } from "../types/interfaces";


const RegisterModal = (props: RegisterModalProps) => {
	const genderOptions = ["Male", "Female"];
	const [profile, setProfile] = useState({username: "", gender: "", age: 50})

	const onCreateUsernameDelete = () => {
		setProfile((prevProfile) => ({...prevProfile, "username": ""}))
	}

	const onCreateProfileChange = (event: any) => {
		event.persist()
		setProfile((prevProfile) => ({...prevProfile, [event.target.name]: event.target.value}))
	}

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
						<FormLabel>Username:</FormLabel>
						<InputGroup>
							<InputGroup.Text>@</InputGroup.Text>
							<FormControl
								placeholder = "Username"
								type = "text"
								name = "username"
								value = {profile.username}
								onChange = {onCreateProfileChange}
							/>

							<Button
								variant = "outline-secondary"
								disabled = {!profile.username.length}
								onClick = {onCreateUsernameDelete}
							>
								X
							</Button>
						</InputGroup>
						{profile.username.length > 10 ?
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
							!profile.username.length ||
							profile.username.length > 10 ||
							!profile.gender.length
						}
						onClick = {() => {
							props.onRegister(profile.username)
							onCreateUsernameDelete()
						}}
					>
						Register
					</Button>
			</Modal.Footer>
		</Modal>
	)
}

export default RegisterModal;