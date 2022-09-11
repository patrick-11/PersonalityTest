import React from "react"

import { Container } from "react-bootstrap"
import { BrowserRouter, Routes, Route, Link } from "react-router-dom"
import Header from "../components/Header"
import Home from "./Home"
import Test from "./Test"
import Averages from "./Averages"
import Users from "./Users"
import UsersResults from "./UsersResults"
import Results from "./Results"
import ResultsShow from "./ResultsShow"
import ResultsUpdate from "./ResultsUpdate"
import Version from "./Version"


const Router = () => {
	return (
		<BrowserRouter>
			<Header/>

			<Container>
				<hr/>
				<Routes>
					<Route path={"/home"} element={<Home/>}/>
					<Route path={"/test"} element={<Test/>}/>
					<Route path={"/averages"} element={<Averages/>}/>
					<Route path={"/users/:id"} element={<UsersResults/>}/>
					<Route path={"/users"} element={<Users/>}/>
					<Route path={"/results/:id/update"} element={<ResultsUpdate/>}/>
					<Route path={"/results/:id"} element={<ResultsShow/>}/>
					<Route path={"/results"} element={<Results/>}/>
					<Route path={"/version"} element={<Version/>}/>
					<Route path={"/"} element={<Link to={"/home"}/>}/>
				</Routes>
				<hr/>
			</Container>
		</BrowserRouter>
	)
}

export default Router