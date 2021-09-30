import React, { useEffect, useState } from "react";


import { Container } from "react-bootstrap";

import RadarChart from "../components/radarChart";
import ResultFilter from "../components/resultFilter";
import { UsersProps, User } from "../types/interfacesRouter";
import { AgeRange } from "../types/interfacesResult";


const Result = (props: UsersProps) => {
	const [filtered, setFiltered] = useState<Array<User>>(props.users)
	const [results, setResults] = useState<Array<number>>([])

	useEffect(() => {setResults(calculateAvgResults(filtered))}, [filtered])

	const calculateAvgResults = (users: Array<User>) => {
		let results = [0,0,0,0,0]
		users.forEach((user) => {
			for(let i = 0; i < user.results.length; i++) {
				results[i] += user.results[i]
			}
		})
		for(let i = 0; i < results.length; i++) {
			let temp = results[i]/users.length
			results[i] = temp
		}
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

export default Result;