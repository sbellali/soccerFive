import { authority } from "./authority";

export interface User {
    id: number,
    username: string;
    email: string;
    photoUrl: string;
    authorities: authority[]
}