#desktop

sc.exe config RPCss start= auto
sc.exe start RPCss
sc.exe config RemoteRegistry start= auto
sc.exe start RemoteRegistry
sc.exe config Schedule start= auto
sc.exe start Schedule
sc.exe config BITS start= auto
sc.exe start BITS
sc.exe config wuauserv start= auto
sc.exe start wuauserv
