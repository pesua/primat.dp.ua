cd /opt
wget primat.asp.dp.ua/snapshot.tar.gz

#sudo -uDr.EniSh liferay-portal-6.0.1/tomcat-6.0.26/bin/shutdown.sh

mv liferay-portal temp/
tar -xzvf snapshot.tar.gz

mysql -ulifedbuser -pFCH9AWqDt2cpDvhQ liferay < liferay.dump
mysql -ulifedbuser -pFCH9AWqDt2cpDvhQ curriculum < curriculum.dump

#sudo -uDr.EniSh /opt/liferay-portal-6.0.1/tomcat-6.0.26/bin/startup.sh

rm snapshot.tar.gz
