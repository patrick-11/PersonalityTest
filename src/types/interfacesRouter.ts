export interface RouterProps {
	users: Array<User>
	inTest: boolean
	user: User
	onInTestChange: (state: boolean) => void
	onUserChange: (prop: string, value: any) => void
	onRegisterShow: (state: boolean) => void
	onLogout: () => void
}

export interface HeaderProps {
	inTest: boolean
	user: User
	onLogout: () => void
	onRegisterShow: (state: boolean) => void
}

export interface UsersProps {
	users: Array<User>
}

export interface RadarChartProps {
	results: Array<number>
}

export interface User {
	name: string
	gender: string
	age: number
	answers: Array<number>
	results: Array<number>
	avgScore: number
}

export type Profile = {
	name: string
	gender: string
	age: number
}

export type Info = {
	testCount: number
	avgScore: number
	sdScore: number
}