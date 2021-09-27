import React from 'react';


import Router from "./screens/Router";
import RegisterModal from "./components/registerModal";
import { Profile } from "./types/interfaces";
import data from "./data/users";


class App extends React.Component {

	state = {
		users: data,
		inTest: false,
		profile: {username: "", gender: "", age: 50},
		answers: [],
		results: [],
		resultShow: false,
		registerShow: false,
		registerFail: ""
	}

	componentDidMount = () => {
		const inTest = JSON.parse(localStorage.getItem("inTest") || "false")
		const profile = JSON.parse(localStorage.getItem("profile") || "null")
		const answers = JSON.parse(localStorage.getItem("answers") || "[]")
		if(inTest) {this.setState({inTest: inTest})}
		if(profile !== null) {this.setState({profile: profile})}
		if(answers.length) {this.setState({answers: answers})}
	}

	onInTestChange = (state: boolean) => {
		this.setState({inTest: state})
		localStorage.setItem("inTest", JSON.stringify(state))
	}

	onProfileChange = (profile: Profile) => {this.setState({profile: profile})}

	onAnswersChange = (answers: Array<string>) => {
		this.setState({answers: answers})
		localStorage.setItem("answers", JSON.stringify(answers))
	}

	onResultShow = (state: boolean) => {
		this.calculateResults()
		this.setState({resultShow: state})
	}

	onResultsChange = (results: Array<number>) => {this.setState({results: results})}

	calculateResults = () => {
		let temp = []
		const reverse = (value: string) => {return (8 - Number(value))}
		temp[0] = (Number(this.state.answers[0]) + reverse(this.state.answers[5]))/2;
		temp[1] = (Number(this.state.answers[6]) + reverse(this.state.answers[1]))/2;
		temp[2] = (Number(this.state.answers[2]) + reverse(this.state.answers[7]))/2;
		temp[3] = (Number(this.state.answers[8]) + reverse(this.state.answers[3]))/2;
		temp[4] = (Number(this.state.answers[4]) + reverse(this.state.answers[9]))/2;
		this.onResultsChange(temp)
	}

	onRegisterShow = (state: boolean) => {this.setState({registerShow: state})}

	onRegisterFail = () => {this.setState({registerFail: ""})}

	onRegister = (username: string) => {
		let profile = this.state.profile
		profile.username = username
		this.onProfileChange(profile)
		this.onRegisterShow(false)
		localStorage.setItem("profile", JSON.stringify(profile))
	}

	onLogout = () => {
		this.onProfileChange({username: "", gender: "", age: 50})
		this.onInTestChange(false)
		this.onAnswersChange([])
		this.onResultsChange([])
		localStorage.clear()
	}

	render = () => {
		return (
			<>
				<Router
					users = {this.state.users}
					inTest = {this.state.inTest}
					profile = {this.state.profile}
					answers = {this.state.answers}
					results = {this.state.results}
					resultShow = {this.state.resultShow}
					onInTestChange = {this.onInTestChange}
					onAnswersChange = {this.onAnswersChange}
					onResultShow = {this.onResultShow}
					onRegisterShow = {this.onRegisterShow}
					onLogout = {this.onLogout}

				/>

				<RegisterModal
					registerShow = {this.state.registerShow}
					registerFail = {this.state.registerFail}
					onRegisterShow = {this.onRegisterShow}
					onRegisterFail = {this.onRegisterFail}
					onRegister = {this.onRegister}
				/>
			</>
		)
	}
}

export default App;