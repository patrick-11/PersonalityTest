import React, { useState } from "react"


import { Container, Button, Form, InputGroup, FormControl, FormGroup, FormLabel, Alert} from "react-bootstrap"

import RadarChart from "../components/radarChart"
import SearchInfo from "../components/searchInfo"
import SearchTable from "../components/searchTable"

import { UsersProps } from "../types/interfacesRouter"
import { User } from "../types/interfacesRouter"


const Search = (props: UsersProps) => {

	const [users, setUsers] = useState(props.users)
	const [name, setName] = useState<string>("")
	const [user, setUser] = useState<User | null>(null)
	const [searchFailed, setSearchFailed] = useState<boolean>(false)


	const filterUsers = (event: any) => {
		const temp = props.users.filter((user) => {return user.name.indexOf(event.target.value) !== -1})
		setName(event.target.value)
		setUsers(temp)
	}

	const searchReset = () => {
		setUsers(props.users)
		setName("")
		setUser(null)
		setSearchFailed(false)
	}

	const seeResults = (user: User) => {
		setUser(user)
		setName(user.name)
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
						<FormLabel>Name:</FormLabel>
						<InputGroup>
							<InputGroup.Text>@</InputGroup.Text>
							<FormControl
								placeholder = "Name"
								type = "text"
								value = {name}
								onChange = {filterUsers}
							/>

							<Button
								variant = "outline-secondary"
								disabled = {!name.length}
								onClick = {() => {searchReset()}}
							>
								X
							</Button>
						</InputGroup>
						{
							name.length > 10 ?
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
				user === null ?
					<SearchTable
						users = {users}
						seeResults = {seeResults}
					/>
					:
					<>
						<SearchInfo
							name = {user.name}
							gender = {user.gender}
							age = {user.age}
							avgScore = {user.avgScore}
						/>
						<hr/>
						<RadarChart
							results = {user.results}
						/>
						<hr/>
						<div className="text-center">
							<Button
								onClick = {() => {searchReset()}}
							>
								Finish
							</Button>
						</div>
					</>
			}
			<hr/>
		</Container>
	)
}

export default Search