import React from "react"


import { Container, Button, Form, InputGroup, FormControl, FormGroup, FormLabel} from "react-bootstrap"

import { SearchBarProps } from "../types/interfacesSearch"


const searchBar = (props: SearchBarProps) => {
	return (
		<Container>
			<Form>
				<FormGroup>
					<FormLabel>Name:</FormLabel>
					<InputGroup>
						<InputGroup.Text>@</InputGroup.Text>
						<FormControl
							placeholder = "Name"
							type = "text"
							value = {props.name}
							onChange = {props.filterUsers}
						/>

						<Button
							variant = "outline-secondary"
							disabled = {!props.name.length}
							onClick = {() => {props.searchReset()}}
						>
							X
						</Button>
					</InputGroup>
					{
						props.name.length > 10 ?
							<Form.Text>Maximum 10 Characters!</Form.Text>
						:
							<></>
					}
				</FormGroup>
			</Form>
		</Container>
	)
}

export default searchBar