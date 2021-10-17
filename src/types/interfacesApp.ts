import { Profile } from "./interfacesRouter"

export interface RegisterModalProps {
	registerShow: boolean
	registerFail: string
	onRegisterShow: (state: boolean) => void
	onRegisterFail: () => void
	onRegister: (profile: Profile) => void
}