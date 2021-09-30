export interface RegisterModalProps {
	registerShow: boolean;
	registerFail: string;
	onRegisterShow: (state: boolean) => void;
	onRegisterFail: () => void;
	onRegister: (username: string) => void;
};