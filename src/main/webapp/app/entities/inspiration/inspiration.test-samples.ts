import { IInspiration, NewInspiration } from './inspiration.model';

export const sampleWithRequiredData: IInspiration = {
  id: 88436,
  nom: 'Frozen Borders',
};

export const sampleWithPartialData: IInspiration = {
  id: 91887,
  nom: 'explicit SQL',
  articleInspiration: 'navigate transmit overriding',
};

export const sampleWithFullData: IInspiration = {
  id: 76946,
  nom: 'engine',
  articleInspiration: 'solution-oriented',
};

export const sampleWithNewData: NewInspiration = {
  nom: 'Shoes Market parse',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
