import dayjs from 'dayjs/esm';
import { ITransaction } from 'app/entities/transaction/transaction.model';
import { ICreateurAfricain } from 'app/entities/createur-africain/createur-africain.model';
import { IDonnateur } from 'app/entities/donnateur/donnateur.model';

export interface IDon {
  id: number;
  description?: string | null;
  dateDon?: dayjs.Dayjs | null;
  transaction?: Pick<ITransaction, 'id' | 'numeroMtn'> | null;
  createurAfricain?: Pick<ICreateurAfricain, 'id' | 'label'> | null;
  donnateur?: Pick<IDonnateur, 'id' | 'prenom'> | null;
}

export type NewDon = Omit<IDon, 'id'> & { id: null };
