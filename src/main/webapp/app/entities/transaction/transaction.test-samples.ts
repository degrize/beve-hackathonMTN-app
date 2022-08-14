import dayjs from 'dayjs/esm';

import { Devise } from 'app/entities/enumerations/devise.model';

import { ITransaction, NewTransaction } from './transaction.model';

export const sampleWithRequiredData: ITransaction = {
  id: 73739,
  numeromtn: 'wireless',
  montant: 52022,
  devise: Devise['DOLLAR'],
  dateTransaction: dayjs('2022-08-13'),
};

export const sampleWithPartialData: ITransaction = {
  id: 73379,
  numeromtn: 'Borders',
  montant: 1828,
  devise: Devise['FCFA'],
  dateTransaction: dayjs('2022-08-13'),
};

export const sampleWithFullData: ITransaction = {
  id: 90439,
  numeromtn: 'open olive',
  montant: 29096,
  devise: Devise['YEN'],
  dateTransaction: dayjs('2022-08-13'),
};

export const sampleWithNewData: NewTransaction = {
  numeromtn: 'Computers clear-thinking Movies',
  montant: 90765,
  devise: Devise['LEONE'],
  dateTransaction: dayjs('2022-08-13'),
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
