find test/res -name '*.png' > list
find test/res -name '*.png' > origin_list

#mkdir tmp
if [ ! -d 'tmp' ];then
	mkdir 'tmp'
fi

cp -r test/res tmp

#test 파일의 png 파일을 모두 삭제
echo 'remove res file...'
while read line; do
	rm $line
done < list

./delta/delta -in_place -test=./test.sh list

#완료 후 list에 존재하는 파일만 복사

while read line; do
	cp 'tmp'${line:4} $line
done < list

