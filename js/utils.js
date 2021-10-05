function isGlobalConfValid() {
  var conf = vm.alarm.config.alertDownlinkConf;
  var confOk = vm.alarm.config.alertDownlinkConfOk;
  var hasDeviceType = conf.deviceTypeId || confOk.deviceTypeId;

  conf.isActivated = !conf.deviceTypeId || conf.deviceTypeId && conf.encoderCommand && (conf.deviceDownlinkRecipients.length || conf.isSelectEmitterObject);
  confOk.isActivated = !confOk.deviceTypeId || confOk.deviceTypeId && confOk.encoderCommand && (confOk.deviceDownlinkRecipients.length || confOk.isSelectEmitterObject);

  var isEncoderCmdValid = !conf.deviceTypeId || conf.encoderCommand && isDef(conf.encoderCommand.parameters, 'value');
  var isEncoderCmdOkValid = !confOk.deviceTypeId || confOk.encoderCommand && isDef(confOk.encoderCommand.parameters, 'value');

  setErrorMsg(conf, confOk, isEncoderCmdValid, isEncoderCmdOkValid);

  return hasDeviceType && conf.isActivated && confOk.isActivated && isEncoderCmdValid && isEncoderCmdOkValid;
}

function setErrorMsg(conf, confOk, isEncoderCmdValid, isEncoderCmdOkValid) {
  var isError = false;
  if (!conf.isActivated && !confOk.isActivated)
	isError = true;
  else if (conf.deviceTypeId && (!conf.isActivated || !isEncoderCmdValid)) {
	isError = true;
	if (vm.selectedModeId !== _MODES_ID.Triggered) {
	  vm.selectedModeId = _MODES_ID.Triggered;
	  onChangeMode(vm.selectedModeId);
	}
  } else if (confOk.deviceTypeId && (!confOk.isActivated || !isEncoderCmdOkValid)) {
	isError = true;
	if (vm.selectedModeId !== _MODES_ID.BackToNormal) {
	  vm.selectedModeId = _MODES_ID.BackToNormal;
	  onChangeMode(vm.selectedModeId);
	}
  }
  vm.errorMsg = isError ? $translate.instant('Alert.ConfigErrors.ValidForm') : '';
}

function isDef(arr, prop) {
  return arr.none(function (a) {
	return angular.isUndefined(a[prop]) || a[prop] === null;
  });
}

deviceService.getDeviceTypeImageUrl(data.widgetCfg.deviceTypeId).subscribe(imgUrl => {
  const reader = new FileReader();
  reader.onload = (e) => this.imgUrl = e?.target?.result;
  reader.readAsDataURL(new Blob([imgUrl], {type: "image/png"}));
});

/**
* Génère un identifiant unique
* src: https://stackoverflow.com/questions/105034/create-guid-uuid-in-javascript
*/
static uuidv4(): string {
return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, c => {
  let r = Math.random() * 16 | 0;
  return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
});

async saveBuilding(building: Building): Promise<Building> {
	let res = await this.dataService.post(this.getBaseUrl(), building, null).pipe(first()).toPromise();
	return new Building(res);
}

getAllScheduledMsgByEncoderCmdIds(encoderCmdIds: number[]): Observable<ScheduledMessage[]> {
	return this.dataService.get(this.getBaseUrl() + '/scheduledMsgByEncoderCmdIds/' + encoderCmdIds)
	.pipe(first(), map(data => data.map((d:Partial<ScheduledMessage>) => new ScheduledMessage(d))));
}

persistScheduledMsgList(scheduledMsgList: ScheduledMessage[]): Observable<ScheduledMessage[]> {
	return this.dataService.post(this.getBaseUrl(), scheduledMsgList)
    .pipe(first(), map(data => data.map((d:Partial<ScheduledMessage>) => new ScheduledMessage(d))));
}