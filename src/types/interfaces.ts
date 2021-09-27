export interface User {
	name: string;
	gender: "Male" | "Female";
	age: number;
	answers: Array<number>;
	results: Array<number>;
	avgScore: number;
};

export type Profile = {
	username: string;
	gender: string;
	age: number;
};

export interface AppProps {
	users: Array<User>;
	inTest: boolean;
	profile: Profile;
	answers: Array<string>;
	results: Array<number>;
	resultShow: boolean;
	onInTestChange: (state: boolean) => void;
	onAnswersChange: (answers: Array<string>) => void;
	onResultShow: (state: boolean) => void;
	onRegisterShow: (state: boolean) => void;
	onLogout: () => void
};

export interface InfoProps {
	info: Info;
}

export type Info = {
	testCount: number;
	avgScore: number;
	sdScore: number;
}

export interface RegisterModalProps {
	registerShow: boolean;
	registerFail: string;
	onRegisterShow: (state: boolean) => void;
	onRegisterFail: () => void;
	onRegister: (username: string) => void;
};

export interface TestProps {
	inTest: boolean;
	profile: Profile;
	answers: Array<string>;
	results: Array<number>;
	resultShow: boolean;
	onInTestChange: (state: boolean) => void;
	onAnswersChange: (answers: Array<string>) => void;
	onResultShow: (state: boolean) => void;
	onRegisterShow: (state: boolean) => void;
}

export interface UsersProps {
	users: Array<User>;
}