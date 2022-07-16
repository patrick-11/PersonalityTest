import React, { useState } from "react"


import { Container } from "react-bootstrap"

import SearchBar from "../components/searchBar"
import RadarChart from "../components/radarChart"
import SearchInfo from "../components/searchInfo"
import SearchTable from "../components/searchTable"
import ButtonCenter from "../components/buttonCenter"

import { UsersProps } from "../types/interfacesRouter"
import { User } from "../types/interfacesRouter"


const Search = (props: UsersProps) => {

	const [users, setUsers] = useState(props.users)
	const [name, setName] = useState<string>("")
	const [user, setUser] = useState<User | null>(null)


	const filterUsers = (event: any) => {
		const temp = props.users.filter((user) => {return user.name.indexOf(event.target.value) !== -1})
		setName(event.target.value)
		setUsers(temp)
	}

	const searchReset = () => {
		setUsers(props.users)
		setName("")
		setUser(null)
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

			{user === null ?
				<>
					<SearchBar
						name = {name}
						filterUsers = {filterUsers}
						searchReset = {searchReset}
					/>
					<hr/>
					<SearchTable
						users = {users}
						seeResults = {seeResults}
					/>
				</>
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
					<ButtonCenter
						name = {"Finish"}
						size = {"lg"}
						variant = {"primary"}
						action = {searchReset}
					/>
					<hr/>
				</>
			}
		</Container>
	)
}

export default Search