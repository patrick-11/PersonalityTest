import React, { useState } from "react"


import { Container, Button, Form, InputGroup, FormControl, FormGroup, FormLabel, Alert} from "react-bootstrap"

import RadarChart from "../components/radarChart"
import SearchInfo from "../components/searchInfo"

import { UsersProps } from "../types/interfacesRouter"
import { User } from "../types/interfacesRouter"


const Search = (props: UsersProps) => {
	const [user, setUser] = useState<User | null>(null)
	const [username, setUsername] = useState<string>("")
	const [searchFailed, setSearchFailed] = useState<boolean>(false)


	const searchUsersResults = () => {
		const user = props.users.find((user) => (user.name === username))
		if(user !== undefined) {
			setSearchFailed(false)
			setUser(user)
		}
		else {setSearchFailed(true)}
	}


	return (
		<Container>
			<hr/>
				<div className="text-center">
					<h2>Search Results of Participants</h2>
				</div>
			<hr/>
			<Container>
				<Form>
					<FormGroup>
						<FormLabel>Username:</FormLabel>
						<InputGroup>
							<InputGroup.Text>@</InputGroup.Text>
							<FormControl
								placeholder = "Username"
								type = "text"
								value = {username}
								onChange = {(event) => setUsername(event.target.value)}
							/>

							<Button
								variant = "outline-secondary"
								disabled = {!username.length}
								onClick = {() => {
									setUsername("")
									setUser(null)
									setSearchFailed(false)
								}}
							>
								X
							</Button>
							<Button
								variant = "outline-secondary"
								disabled = {!username.length || username.length > 10}
								onClick = {searchUsersResults}
							>
								Search
							</Button>
						</InputGroup>
						{
							username.length > 10 ?
								<Form.Text>Maximum 10 Characters!</Form.Text>
							:
								<></>
						}
					</FormGroup>
				</Form>
			</Container>
			<hr/>
			{
				searchFailed ?
				<Alert variant = "danger">This username does not exist!</Alert>
				:
				user !== null ?
					<>
						<SearchInfo
							gender = {user.gender}
							age = {user.age}
							avgScore = {user.avgScore}
						/>
						<hr/>
						<RadarChart
							results = {user.results}
						/>
					</>
					:
					<></>
			}
		</Container>
	)
}

export default Search