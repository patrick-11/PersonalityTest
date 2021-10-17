import React, { useEffect, useState } from "react"


import { Container } from "react-bootstrap"

import RadarChart from "../components/radarChart"
import ResultFilter from "../components/resultFilter"

import { UsersProps } from "../types/interfacesRouter"
import { AgeRange } from "../types/interfacesResult"
import { User } from "../types/interfacesRouter"


const Result = (props: UsersProps) => {
	const [filtered, setFiltered] = useState<Array<User>>(props.users)
	const [results, setResults] = useState<Array<number>>([])


	useEffect(() => {setResults(calculateAvgResults(filtered))}, [filtered])


	const calculateAvgResults = (users: Array<User>) => {
		let results = [0,0,0,0,0]
		users.forEach((user) => {user.results.forEach((result, index) => {results[index] += result})})
		results.forEach((result, index) => {results[index] = result/users.length})
		return results
	}

	const onChangeFilter = (gender: Array<string>, age: AgeRange) => {
		const usersFiltered = props.users.filter((user) => gender.includes(user.gender) && Number(age.minAge) <= user.age && user.age<= Number(age.maxAge))
		setFiltered(usersFiltered)
	}

	return (
		<Container>
			<hr/>
				<div className="text-center">
					<h2>Average Results of {filtered.length} Participants</h2>
				</div>
			<hr/>
			<ResultFilter
				onChangeFilter = {onChangeFilter}
			/>
			<hr/>
			<RadarChart
				results = {results}
			/>
			<hr/>
		</Container>
	)
}

export default Result