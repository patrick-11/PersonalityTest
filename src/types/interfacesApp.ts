import { Profile } from "./interfacesRouter"

export interface RegisterModalProps {
	registerShow: boolean
	registerFail: string
	onRegisterShow: (state: boolean) => void
	onRegisterFail: () => void
	onRegister: (profile: Profile) => void
}

export interface ButtonCenterProps {
	name: string
	variant: string
	size: undefined | "sm" | "lg" 
	action: () => void
}