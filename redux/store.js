import {configureStore, getDefaultMiddleware} from '@reduxjs/toolkit';
import reducer from './reducers';
import logger from './middlewares/logger';

export default configureStore({
  reducer,
  middleware: [
    ...getDefaultMiddleware({
      serializableCheck: false,
    }),
    logger,
  ],
  //   middleware: getDefaultMiddleware => getDefaultMiddleware().concat(logger),
  // devTools: process.env.NODE_ENV !== 'production',
});
