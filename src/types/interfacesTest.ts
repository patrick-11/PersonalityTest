import { User } from "./interfacesRouter"

export interface TestProps {
	inTest: boolean
	user: User
	onInTestChange: (state: boolean) => void
	onUserChange: (prop: string, value: any) => void
	onRegisterShow: (state: boolean) => void
}

export interface TestStartProps {
	user: User
	onInTestChange: (state: boolean) => void
	onRegisterShow: (state: boolean) => void
}

export interface TestQuestionsProps {
	user: User
	onInTestChange: (state: boolean) => void
	onUserChange: (prop: string, value: any) => void
}