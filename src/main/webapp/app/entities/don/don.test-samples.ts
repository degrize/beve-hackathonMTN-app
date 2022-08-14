import dayjs from 'dayjs/esm';

import { IDon, NewDon } from './don.model';

export const sampleWithRequiredData: IDon = {
  id: 8455,
};

export const sampleWithPartialData: IDon = {
  id: 12062,
  description: 'payment Loire',
  dateDon: dayjs('2022-08-13'),
};

export const sampleWithFullData: IDon = {
  id: 78793,
  description: 'Salad Peso',
  dateDon: dayjs('2022-08-13'),
};

export const sampleWithNewData: NewDon = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
