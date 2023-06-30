class SessionContext:
    def __init__(self, session):
        self.session = session

    def __enter__(self):
        return self.session
        
    def __exit__(self, exc_type, exc_val, exc_tb):
        self.session.close()