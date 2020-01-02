filename=tmp.apk

#원본에서 리소스 복사
while read line; do
	cp 'tmp'${line:4} $line
done < list

#apk 생성 시도
if [ -f $filename ]; then
	rm $filename
fi

apktool b test -o $filename

while read line; do
	if [ -f $line ]; then
		rm $line
	fi
done < origin_list

#생성이 됐다면 성공, 생성이 되지 않는다면 실패.
if  [ -f $filename ]; then
	echo "success"
	exit 0
else
	echo "failed"
	exit 1
fi
