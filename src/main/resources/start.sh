host="127.0.0.1"
port=25565

while true ; do
    java -Dhost=$host -Dport=$port -jar micro.jar
    exitcode="$?"
    if [ $exitcode -ne "99" ]; then
        echo "
Server exit code: $exitcode"
        break
    fi
    echo "

 === SERVER RESTART ===

"
done