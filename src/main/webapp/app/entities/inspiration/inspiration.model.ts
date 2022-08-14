import { ICreateurAfricain } from 'app/entities/createur-africain/createur-africain.model';

export interface IInspiration {
  id: number;
  nom?: string | null;
  articleInspiration?: string | null;
  createurAfricains?: Pick<ICreateurAfricain, 'id' | 'label'>[] | null;
}

export type NewInspiration = Omit<IInspiration, 'id'> & { id: null };
