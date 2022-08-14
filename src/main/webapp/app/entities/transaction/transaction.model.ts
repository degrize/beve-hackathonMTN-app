import dayjs from 'dayjs/esm';
import { Devise } from 'app/entities/enumerations/devise.model';

export interface ITransaction {
  id: number;
  numeroMtn?: string | null;
  montant?: number | null;
  devise?: Devise | null;
  dateTransaction?: dayjs.Dayjs | null;
}

export type NewTransaction = Omit<ITransaction, 'id'> & { id: null };
