mkdir /tmp/server-backup                        
cd /tmp/server-backup                         
             
cp -r /opt/liferay-portal /tmp/server-backup
mysqldump --single-transaction -u lifedbuser -pFCH9AWqDt2cpDvhQ liferay > liferay.dump
mysqldump --single-transaction -u lifedbuser -pFCH9AWqDt2cpDvhQ curriculum > curriculum.dump

tar -cvzf liferay.tar.gz liferay-portal liferay.dump curriculum.dump
cp -f liferay.tar.gz /var/www/snapshot.tar.gz

filename=$(date +%y-%m-%d).tar.gz
cp liferay.tar.gz ~/backup/liferay/$filename

rm -R /tmp/server-backup