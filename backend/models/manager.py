def create( session, table ):
    session.add(table)
    session.commit()

def get(session, table, filter) :
    return session.query(table).filter(filter).all()

