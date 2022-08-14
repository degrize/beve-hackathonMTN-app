import { ICreateurAfricain } from 'app/entities/createur-africain/createur-africain.model';
import { EtatCompte } from 'app/entities/enumerations/etat-compte.model';

export interface ISouscription {
  id: number;
  etat?: EtatCompte | null;
  montant?: number | null;
  pourcentageDuDon?: number | null;
  createurAfricains?: Pick<ICreateurAfricain, 'id' | 'label'>[] | null;
}

export type NewSouscription = Omit<ISouscription, 'id'> & { id: null };
