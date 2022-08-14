import { INatureCreateur, NewNatureCreateur } from './nature-createur.model';

export const sampleWithRequiredData: INatureCreateur = {
  id: 31579,
};

export const sampleWithPartialData: INatureCreateur = {
  id: 27227,
};

export const sampleWithFullData: INatureCreateur = {
  id: 54543,
  type: 'Licensed Fresh',
  description: 'Steel',
};

export const sampleWithNewData: NewNatureCreateur = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
