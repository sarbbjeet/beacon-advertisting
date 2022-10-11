export default store => next => action => {
  if (typeof action === 'function') action();
  // if (action.type !== 'apiCallBegin') return next(action);

  //const {dispatch,setState}= store ;
  //const {url, data} = action.payload;
  //dispatch({type: onStart})

  console.log('state', store.getState());
  return next(action);
};
