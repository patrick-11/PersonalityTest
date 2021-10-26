import React, { useState } from "react"


import { Container, Row, Col, Form, FormGroup, FormLabel, ToggleButtonGroup, ToggleButton, InputGroup } from "react-bootstrap"

import { ResultFilterProps, AgeRange } from "../types/interfacesResult"


const ResultFilter = (props: ResultFilterProps) => {
	const [gender, setGender] = useState(["Male", "Female"])
	const [age, setAge] = useState({minAge: "5", maxAge: "120"})

	const onChangeGender = (gender: Array<string>) => {
		setGender(gender)
		props.onChangeFilter(gender, age)
	}
	
	const onChangeAge = (age: AgeRange) => {
		setAge(age)
		props.onChangeFilter(gender, age)
	}

	return (
		<Container>
			<Form>
				<Row>
					<Col>
						<FormGroup>
							<FormLabel>Gender:</FormLabel>
							<InputGroup>
								<ToggleButtonGroup type="checkbox" value={gender} onChange={onChangeGender}>
									<ToggleButton
										id="1"
										variant = "outline-primary"
										value={"Male"}
									>
										Male
									</ToggleButton>
									<ToggleButton
										id="2"
										variant = "outline-primary"
										value={"Female"}
									>
										Female
									</ToggleButton>
								</ToggleButtonGroup>
							</InputGroup>
						</FormGroup>
					</Col>

					<Col>
						<Row>
							<Col>
								<InputGroup>
									<FormLabel>Minimum age: {age.minAge}</FormLabel>
									<Form.Range
										id = "minAge"
										name = "minAge"
										step = "1"
										value = {age.minAge}
										min = "5"
										max = {age.maxAge}
										onChange = {(event) => {onChangeAge({...age, [event.target.name]: event.target.value})}}
									/>
								</InputGroup>
							</Col>
							<Col>
								<InputGroup>
									<FormLabel>Maximum age: {age.maxAge}</FormLabel>
									<Form.Range
										id = "maxAge"
										name = "maxAge"
										step = "1"
										value = {age.maxAge}
										min = {age.minAge}
										max = "120"
										onChange = {(event) => {onChangeAge({...age, [event.target.name]: event.target.value})}}
									/>
								</InputGroup>
							</Col>
						</Row>
					</Col>
				</Row>
			</Form>
		</Container>
	)
}

export default ResultFilter