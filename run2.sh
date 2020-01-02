timestamp=`date +%Y%m%d%H%M`


find test/res -name '*.xml' > list2
find test/res -name '*.xml' > origin_list2

#mkdir tmp
if [ ! -d 'tmp' ];then
	mkdir 'tmp'
fi

cp -r test/res tmp

#test 파일의 xml 파일을 모두 삭제
echo 'remove res file...'
while read line; do
	rm $line
done < list2

./delta/delta -in_place -test=./test2.sh list2

#완료 후 list에 존재하는 파일만 복사

while read line; do
	cp 'tmp'${line:4} $line
done < list2

timestamp2=`date +%Y%m%d%H%M`

echo "start = "$timestamp
echo "end = "$timestamp2
